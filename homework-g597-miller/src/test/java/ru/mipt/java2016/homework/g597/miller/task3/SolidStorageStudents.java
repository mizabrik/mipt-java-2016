package ru.mipt.java2016.homework.g597.miller.task3;

import java.io.DataInput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import ru.mipt.java2016.homework.tests.task2.Student;
import ru.mipt.java2016.homework.tests.task2.StudentKey;

/*
 * Created by Vova Miller on 20.11.2016.
 */
public class SolidStorageStudents extends SolidStorageAbstract<StudentKey, Student> {

    public SolidStorageStudents(String directoryName) throws IOException {
        super(directoryName);
    }

    @Override
    protected StudentKey readKey(DataInput f) throws IOException {
        try {
            int groupId = f.readInt();
            int n = f.readInt();
            if (n < 0) {
                throw new IOException("Invalid storage file.");
            }
            byte[] name = new byte[n];
            f.readFully(name, 0, n);
            return new StudentKey(groupId, new String(name, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    protected Student readValue(DataInput f) throws IOException {
        try {
            int n;
            int groupId = f.readInt();
            n = f.readInt();
            if (n < 0) {
                throw new IOException("Invalid storage file.");
            }
            byte[] name = new byte[n];
            f.readFully(name, 0, n);
            n = f.readInt();
            if (n < 0) {
                throw new IOException("Invalid storage file.");
            }
            byte[] hometown = new byte[n];
            f.readFully(hometown, 0, n);
            long time = f.readLong();
            Date birthDate = new Date();
            birthDate.setTime(time);
            boolean hasDormitory = f.readBoolean();
            double averageScore = f.readDouble();
            return new Student(groupId, new String(name, StandardCharsets.UTF_8),
                    new String(hometown, StandardCharsets.UTF_8), birthDate,
                    hasDormitory, averageScore);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void writeKey(StudentKey key) throws IOException {
        try {
            file.writeInt(key.getGroupId());
            byte[] name = key.getName().getBytes();
            file.writeInt(name.length);
            file.write(name);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void writeValue(Student value) throws IOException {
        try {
            // Group ID
            file.writeInt(value.getGroupId());
            // Name
            byte[] name = value.getName().getBytes();
            file.writeInt(name.length);
            file.write(name);
            // Hometown
            byte[] hometown = value.getHometown().getBytes();
            file.writeInt(hometown.length);
            file.write(hometown);
            // Birth Date
            file.writeLong(value.getBirthDate().getTime());
            // Has Dormitory
            file.writeBoolean(value.isHasDormitory());
            // Average Score
            file.writeDouble(value.getAverageScore());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}