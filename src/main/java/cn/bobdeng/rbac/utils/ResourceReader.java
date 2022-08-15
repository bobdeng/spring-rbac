package cn.bobdeng.rbac.utils;

import java.io.IOException;

public interface ResourceReader {
    String read(String path)throws IOException;
}
