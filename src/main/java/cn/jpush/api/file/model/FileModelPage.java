package cn.jpush.api.file.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author daixuan
 * @version 2020/2/23 20:18
 */
@Data
public class FileModelPage {

    @Expose
    private int total_count;
    @Expose
    private List<FileModel> files;

}
