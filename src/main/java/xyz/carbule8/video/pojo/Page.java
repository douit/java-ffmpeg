package xyz.carbule8.video.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    private Integer pageNum; // 页码

    private Integer pageSize; // 数量
}
