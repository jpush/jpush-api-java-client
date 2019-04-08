package cn.jiguang.common.utils.sm2;

import cn.jiguang.common.utils.Base64;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.gm.SM2P256V1Curve;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import java.util.concurrent.locks.ReentrantLock;

public class SM2Util extends GMBaseUtil {
    //////////////////////////////////////////////////////////////////////////////////////
    /*
     * 以下为SM2推荐曲线参数
     */
    public static final SM2P256V1Curve CURVE = new SM2P256V1Curve();
    public final static BigInteger SM2_ECC_P = CURVE.getQ();
    public final static BigInteger SM2_ECC_A = CURVE.getA().toBigInteger();
    public final static BigInteger SM2_ECC_B = CURVE.getB().toBigInteger();
    public final static BigInteger SM2_ECC_N = CURVE.getOrder();
    public final static BigInteger SM2_ECC_H = CURVE.getCofactor();
    public final static BigInteger SM2_ECC_GX = new BigInteger(
        "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7", 16);
    public final static BigInteger SM2_ECC_GY = new BigInteger(
        "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0", 16);
    public static final ECPoint G_POINT = CURVE.createPoint(SM2_ECC_GX, SM2_ECC_GY);
    public static final ECDomainParameters DOMAIN_PARAMS = new ECDomainParameters(CURVE, G_POINT,
        SM2_ECC_N, SM2_ECC_H);
    public static final int CURVE_LEN = BCECUtil.getCurveLength(DOMAIN_PARAMS);
    //////////////////////////////////////////////////////////////////////////////////////

    public static final EllipticCurve JDK_CURVE = new EllipticCurve(new ECFieldFp(SM2_ECC_P), SM2_ECC_A, SM2_ECC_B);
    public static final java.security.spec.ECPoint JDK_G_POINT = new java.security.spec.ECPoint(
        G_POINT.getAffineXCoord().toBigInteger(), G_POINT.getAffineYCoord().toBigInteger());
    public static final java.security.spec.ECParameterSpec JDK_EC_SPEC = new java.security.spec.ECParameterSpec(
        JDK_CURVE, JDK_G_POINT, SM2_ECC_N, SM2_ECC_H.intValue());

    //////////////////////////////////////////////////////////////////////////////////////

    private static ECPublicKeyParameters RUNNING_PUBLIC_KEY;
    private static ReentrantLock LOCK = new ReentrantLock();

    /**
     * 加密数据
     * @param srcData
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String srcData, String publicKey) throws Exception {
        if (RUNNING_PUBLIC_KEY == null) {
            try {
                LOCK.lock();
                if (RUNNING_PUBLIC_KEY == null) {
                    ECPoint ecPoint = CURVE.decodePoint(Base64.decode(publicKey.toCharArray()));
                    RUNNING_PUBLIC_KEY = new ECPublicKeyParameters(ecPoint, DOMAIN_PARAMS);
                }
            } catch (Exception e) {
                throw new RuntimeException("init public key error", e);
            } finally {
                LOCK.unlock();
            }
        }
        return encrypt(RUNNING_PUBLIC_KEY, srcData.getBytes());
    }

    /**
     * 生成ECC密钥对
     *
     * @return ECC密钥对
     */
    public static AsymmetricCipherKeyPair generateKeyPairParameter() {
        SecureRandom random = new SecureRandom();
        return BCECUtil.generateKeyPairParameter(DOMAIN_PARAMS, random);
    }

    public static KeyPair generateKeyPair() throws NoSuchProviderException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException {
        SecureRandom random = new SecureRandom();
        return BCECUtil.generateKeyPair(DOMAIN_PARAMS, random);
    }

    /**
     * 只获取私钥里的d，32字节
     *
     * @param privateKey
     * @return
     */
    public static byte[] getRawPrivateKey(BCECPrivateKey privateKey) {
        return fixToCurveLengthBytes(privateKey.getD().toByteArray());
    }

    /**
     * 只获取公钥里的XY分量，64字节
     *
     * @param publicKey
     * @return
     */
    public static byte[] getRawPublicKey(BCECPublicKey publicKey) {
        byte[] src65 = publicKey.getQ().getEncoded(false);
        byte[] rawXY = new byte[CURVE_LEN * 2];//SM2的话这里应该是64字节
        System.arraycopy(src65, 1, rawXY, 0, rawXY.length);
        return rawXY;
    }

    public static byte[] encrypt(BCECPublicKey pubKey, byte[] srcData) throws InvalidCipherTextException {
        ECPublicKeyParameters pubKeyParameters = BCECUtil.convertPublicKeyToParameters(pubKey);
        return encrypt(pubKeyParameters, srcData);
    }

    /**
     * ECC公钥加密
     *
     * @param pubKeyParameters ECC公钥
     * @param srcData          源数据
     * @return SM2密文，实际包含三部分：ECC公钥、真正的密文、公钥和原文的SM3-HASH值
     * @throws InvalidCipherTextException
     */
    public static byte[] encrypt(ECPublicKeyParameters pubKeyParameters, byte[] srcData)
        throws InvalidCipherTextException {
        SM2Engine engine = new SM2Engine();
        ParametersWithRandom pwr = new ParametersWithRandom(pubKeyParameters, new SecureRandom());
        engine.init(true, pwr);
        return engine.processBlock(srcData, 0, srcData.length);
    }

    public static byte[] decrypt(BCECPrivateKey priKey, byte[] sm2Cipher) throws InvalidCipherTextException {
        ECPrivateKeyParameters priKeyParameters = BCECUtil.convertPrivateKeyToParameters(priKey);
        return decrypt(priKeyParameters, sm2Cipher);
    }

    /**
     * ECC私钥解密
     *
     * @param priKeyParameters ECC私钥
     * @param sm2Cipher        SM2密文，实际包含三部分：ECC公钥、真正的密文、公钥和原文的SM3-HASH值
     * @return 原文
     * @throws InvalidCipherTextException
     */
    public static byte[] decrypt(ECPrivateKeyParameters priKeyParameters, byte[] sm2Cipher)
        throws InvalidCipherTextException {
        SM2Engine engine = new SM2Engine();
        engine.init(false, priKeyParameters);
        return engine.processBlock(sm2Cipher, 0, sm2Cipher.length);
    }

    private static byte[] fixToCurveLengthBytes(byte[] src) {
        if (src.length == CURVE_LEN) {
            return src;
        }

        byte[] result = new byte[CURVE_LEN];
        if (src.length > CURVE_LEN) {
            System.arraycopy(src, src.length - result.length, result, 0, result.length);
        } else {
            System.arraycopy(src, 0, result, result.length - src.length, src.length);
        }
        return result;
    }

    public static void main(String[] args) throws InvalidCipherTextException, IOException {
//        AsymmetricCipherKeyPair keyPair = SM2Util.generateKeyPairParameter();
//        ECPrivateKeyParameters priKey1 = (ECPrivateKeyParameters) keyPair.getPrivate();
//        ECPublicKeyParameters pubKey1 = (ECPublicKeyParameters) keyPair.getPublic();
//
//        byte[] src65 = pubKey1.getQ().getEncoded(false);
//        byte[] rawXY = new byte[CURVE_LEN * 2];//SM2的话这里应该是64字节
//        System.arraycopy(src65, 1, rawXY, 0, rawXY.length);
//        System.out.println("反转: " + String.valueOf(Base64.encode(rawXY)));
//
//        System.out.println("私钥: " + String.valueOf(Base64.encode(priKey1.getD().toByteArray())));
//        System.out.println("公钥: " + String.valueOf(Base64.encode(pubKey1.getQ().getEncoded(false))));

        ECPoint ecPoint = CURVE.decodePoint(Base64.decode("BPj6Mj/T444gxPaHc6CDCizMRp4pEl14WI2lvIbdEK2c+5XiSqmQt2TQc8hMMZqfxcDqUNQW95puAfQx1asv3rU=".toCharArray()));
        ECPublicKeyParameters pubKey = new ECPublicKeyParameters(ecPoint, DOMAIN_PARAMS);
        byte[] encryptedData = SM2Util.encrypt(pubKey, "{\"platffffddfaafffffffffffffffffffffffffffffffffffff".getBytes());
        System.out.println("SM2 encrypt result:\n" + String.valueOf(Base64.encode(encryptedData)));

//        BigInteger bigInteger = new BigInteger(Base64.decode("OUgn4s25RWq/XFl4MNiZjCGzPTvsthvJYZc1ApbUTjg==".toCharArray()));
//        ECPrivateKeyParameters priKey = new ECPrivateKeyParameters(bigInteger, DOMAIN_PARAMS);
//        byte[] decryptedData = SM2Util.decrypt(priKey, Base64.decode("BODUJdQBoNMvzm+GSrQTxx4cVhj969xS5TitrPbSd81iB/OPShD2Da4KlCaYAlU8v0J7KiwwZ1iOoQw8VeM5YmtSmHk0Hvx8Pfm+e1+NGI721caEXmlihG+CaM0VGZFsM6W7Zwg9eIHPgbQWvgIDU8NaRNeeU6w2aDRlrzlTKUewQxcbvkx6Veu4Cbc+5LlIyCfFYTeHStrbeFct08YwhlfAJ6wuEyY5IIhZjx2SEvGY3b9EK/qndofFkXeTamrm5K0ZMXGFSALtWybc2/Wrt0Zlsz6FwNSkXXI6o4brCMMPLGne3Y901F4RyciH5ePYHOHr61yv1t8peIIf5Cl5kedSwhWNxQZcvy+QvN77Rs6KxZ+B3Zq0WggVSjzjmbaVK5moG3Be3/tXOA7ld4ceZ8aj4MJFJwWjPcMeVJtMEKgjN7n9c8JNLpIrSSISkxz3W6gxtL1Wr7QhVGfFnHSn02rA7DD2kCxxgCP0FvR/uap4J6aF279Cn6jEek7Ze4sZOuhKNY3l/YBTzfHj".toCharArray()));
//        System.out.println("SM2 decrypt result:\n" + ByteUtils.toHexString(decryptedData));
    }
}
