package com.wtf.tool.util.copy;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CopyUtils {

    @FunctionalInterface
    public interface ListCallback {
      List<?> getList();
    }
    @FunctionalInterface
    public interface AssignObjectCallback<T> {
      void doAssign(T t);
    }


//    public List<UserDTO> listUsers(UserParam queryUserParam) {
//        return userMapper.listUsers(queryUserParam).stream().map(userDO -> {
//            UserDTO userDTO = new UserDTO();
//            BeanUtils.copyProperties(userDO, userDTO);
//            return userDTO;
//        }).collect(Collectors.toList());
//
//    }

    //创建泛型对象
    private static  <T> T createGeneric(Class<T> clazz) {
        T generic = null;
        try {
            generic = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return generic;
    }

    public static <T> List<T> beanCovert(ListCallback callback, Class<T> c) {
        List<?> list = callback.getList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(d -> {
            T generic  = createGeneric(c);
            BeanUtils.copyProperties(d, generic);
            return generic;
        }).collect(Collectors.toList());
    }
    public static <T> List<T> beanCovert(List<?> list, Class<T> c) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(d -> {
            T generic  = createGeneric(c);
            BeanUtils.copyProperties(d, generic);
            return generic;
        }).collect(Collectors.toList());
    }
    // 给转换的对象一个赋值的机会
    public static <T> List<T> beanCovert(List<?> list, Class<T> c, AssignObjectCallback<T> assignObjectCallback) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(d -> {
            T generic  = createGeneric(c);
            BeanUtils.copyProperties(d, generic);
            assignObjectCallback.doAssign(generic);
            return generic;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        UserDO userDO1 = new UserDO(1L, "user1");
        UserDO userDO2 = new UserDO(2L, "user2");
        List<UserDO> userDOList = new ArrayList<>();
        userDOList.add(userDO1);
        userDOList.add(userDO2);
        System.out.println((beanCovert(userDOList, UserDTO.class).get(0)).getName());
//        System.out.println((beanCovert(() -> userDOList, UserDTO.class).get(0)).getName());
        System.out.println((beanCovert(userDOList, UserDTO.class, (userDTO) -> {
            userDTO.setName("copyUser");
        }).get(0)).getName());


    }
}
