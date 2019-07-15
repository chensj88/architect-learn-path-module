package org.chen.architect.google.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author chensj
 * @version v1.0
 * @classDesc protobuf 构造对象，序列化，反序列化，获取对象
 * @project java-netty-module
 * @email chenshijie1988@yeah.net
 * @date 2019-07-15 21:17
 */
public class StudentListTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        // 构建对象
        StudentList.Student student = StudentList.Student.newBuilder()
                .setAddress("合肥")
                .setAge(20)
                .setId(1)
                .setName("demo")
                .build();
        // 序列化
        byte[] student2ByteArray = student.toByteArray();

        // 反序列化
        StudentList.Student student1 =
                StudentList.Student.parseFrom(student2ByteArray);
        // 获取对象
        System.out.println(student1);

    }
}
