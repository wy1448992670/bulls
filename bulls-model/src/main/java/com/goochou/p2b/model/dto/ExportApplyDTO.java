package com.goochou.p2b.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年11月14日 16:07:00
 */
public class ExportApplyDTO implements Serializable {

    private static final long serialVersionUID = 1083066957714356533L;
    
    private String applyReason;

    private Integer encrypt;
//    private Boolean encrypt;

    private String encryptColumn;
//    private List<String> encryptColumn;
    
    private List<Map> exportApplyConditions;

    private List<Map> exportApplyColumns;

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Integer encrypt) {
        this.encrypt = encrypt;
    }

    public String getEncryptColumn() {
        return encryptColumn;
    }

    public void setEncryptColumn(String encryptColumn) {
        this.encryptColumn = encryptColumn;
    }

    public List<Map> getExportApplyConditions() {
        return exportApplyConditions;
    }

    public void setExportApplyConditions(List<Map> exportApplyConditions) {
        this.exportApplyConditions = exportApplyConditions;
    }

    public List<Map> getExportApplyColumns() {
        return exportApplyColumns;
    }

    public void setExportApplyColumns(List<Map> exportApplyColumns) {
        this.exportApplyColumns = exportApplyColumns;
    }
}
