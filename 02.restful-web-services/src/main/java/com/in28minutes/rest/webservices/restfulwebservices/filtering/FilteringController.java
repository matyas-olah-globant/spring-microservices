package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.filterOutAllExcept;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    // field1, field2
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return filterOutFieldsFromBeanExcept(someBean, "field1", "field2");
    }

    // field2, field3
    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBeans() {
        List<SomeBean> list = Arrays.asList(
                new SomeBean("value11", "value12", "value13"),
                new SomeBean("value21", "value22", "value23"));
        return filterOutFieldsFromBeanExcept(list, "field2", "field3");
    }

    private MappingJacksonValue filterOutFieldsFromBeanExcept(Object object, String... fields) {
        SimpleBeanPropertyFilter filter = filterOutAllExcept(fields);
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(object);
        mapping.setFilters(filters);
        return mapping;
    }

}
