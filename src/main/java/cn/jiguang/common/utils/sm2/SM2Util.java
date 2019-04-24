package cn.jiguang.common.utils.sm2;

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

}
