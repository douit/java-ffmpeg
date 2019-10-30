package xyz.carbule8.video.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult {
    private Integer code; // 状态码

    private String msg; // 消息

    private Object data; // 数据
}
