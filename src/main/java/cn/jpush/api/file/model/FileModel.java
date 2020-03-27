package cn.jpush.api.file.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.Date;

/**
 * @author daixuan
 * @version 2020/2/23 20:18
 */
@Data
public class FileModel {
    @Expose
    private String file_id;
    @Expose
    private String type;
    @Expose
    private Date create_time;

}
