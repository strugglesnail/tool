package com.wtf.tool.util.date;

import com.wtf.tool.model.UserDO;
import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wang_tengfei
 * @desc 用于生成统计图表数据的工具类
 */
public class StatisticsUtils {

    @FunctionalInterface
    public interface DateCallBack<T> {
        T populateDateIfMatch(String date);
    }

    /**
     *
     * @param yearDate
     * @param callBack
     * @param <T>
     * @return 填充好的数据列表
     */
    public static <T> List<T> statisticsDataGenerator(String yearDate, DateCallBack<T> callBack) {
        List<String> dateList = DateUtils.getDefaultYearMonthDate(yearDate);
        List<T> list = new ArrayList<>(dateList.size());
        for (String date: dateList) {
            list.add(callBack.populateDateIfMatch(date));
        }
        return list;
    }

    public static void main(String[] args) throws IntrospectionException {
        UserDO userDO1 = new UserDO(1L, "user1", "2020-01");
        UserDO userDO2 = new UserDO(2L, "user2", "2020-02");
        UserDO userDO3 = new UserDO(3L, "user3", "2020-03");
        UserDO userDO4 = new UserDO(4L, "user4", "2020-04");
        UserDO userDO5 = new UserDO(5L, "user5", "2020-05");
        UserDO userDO6 = new UserDO(6L, "user6", "2020-06");
        List<UserDO> userList = new ArrayList<>();
        userList.add(userDO1);
//        userList.add(userDO2);
//        userList.add(userDO3);
        userList.add(userDO4);
//        userList.add(userDO5);
        userList.add(userDO6);
        UserDO userDO = userList.stream().filter(user -> "2020-06".equals(user.getDate())).findFirst().get();
//        List<UserDO> userDOS = statisticsDataGenerator("2020-06-18", date -> {
//            UserDO userDO = userList.stream().filter(user -> date.equals(user.getDate())).findFirst().orElse(new UserDO());
//            if (StringUtils.isEmpty(userDO.getDate())) userDO.setDate(date);
//            return userDO;
//        });
//        System.out.println(userDOS);

        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(userDO.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            System.out.println(descriptor.getPropertyType());
        }

    }
}
