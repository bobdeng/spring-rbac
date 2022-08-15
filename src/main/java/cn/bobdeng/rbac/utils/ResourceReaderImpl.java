package cn.bobdeng.rbac.utils;

import com.google.common.io.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ResourceReaderImpl implements ResourceReader {
    @Override
    public String read(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
    }
}
