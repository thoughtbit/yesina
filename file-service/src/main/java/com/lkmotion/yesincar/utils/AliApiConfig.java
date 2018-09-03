package com.lkmotion.yesincar.utils;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author liheng
 * @date 2018/8/21
 */
@Component
@ConfigurationProperties(prefix = "apikey", ignoreInvalidFields = true)
@Setter
public class AliApiConfig {
    private List<Map<String, String>> ali = new ArrayList<>();
    private static final String KEY_ID ="key_id";
    private static final String KEY_SECRET = "key_secret";
    private static final String POOL_KEY = "pool_key";
    private static final String FILE_PATH="file_path";

    public String get(String key) {
        return ali.stream().filter(m -> m.containsKey(key)).findFirst().orElse(new HashMap<>(0)).get(key);
    }
    public String getKeyId(){return  get(KEY_ID);}
    public String getKeySecret(){return  get(KEY_SECRET);}
    public String getPoolKey(){return get(POOL_KEY);}
    public String getFilePath(){return get(FILE_PATH);}
}
