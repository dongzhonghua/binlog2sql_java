package com.seewo.binlogsql.vo;

import java.util.List;

import lombok.Data;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
@Data
public class RowVo {
    private List<ColumnItemDataVo> value;
}
