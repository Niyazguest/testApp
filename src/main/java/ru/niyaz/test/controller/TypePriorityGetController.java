package ru.niyaz.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.niyaz.test.dao.PriorityDao;
import ru.niyaz.test.dao.TypeDao;
import ru.niyaz.test.entity.Priority;
import ru.niyaz.test.entity.Type;
import ru.niyaz.test.pojo.TypePriorityObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.09.15.
 */

@Controller
public class TypePriorityGetController {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private PriorityDao priorityDao;

    @RequestMapping(value = "/getTypes", method = RequestMethod.GET)
    public @ResponseBody List<TypePriorityObject> getTypes(HttpServletRequest request, HttpServletResponse response) {
        List<Type> types = typeDao.getTypes();
        List<TypePriorityObject> typesList = new ArrayList<TypePriorityObject>();
        TypePriorityObject typeObject;
        for (Type type : types) {
            typeObject = new TypePriorityObject(type.getTypeId().intValue(), type.getName());
            typesList.add(typeObject);
        }
        return typesList;
    }

    @RequestMapping(value = "/getPriorities", method = RequestMethod.GET)
    public @ResponseBody List<TypePriorityObject> getPriorities(HttpServletRequest request, HttpServletResponse response) {
        List<Priority> priorities = priorityDao.getPriorities();
        List<TypePriorityObject> prioritiesList = new ArrayList<TypePriorityObject>();
        TypePriorityObject priorityObject;
        for (Priority priority : priorities) {
            priorityObject = new TypePriorityObject(priority.getPriorityId().intValue(), priority.getName());
            prioritiesList.add(priorityObject);
        }
        return prioritiesList;
    }
}
