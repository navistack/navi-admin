package org.navistack.admin.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.experimental.UtilityClass;
import org.navistack.framework.data.Page;
import org.navistack.framework.data.PageImpl;
import org.navistack.framework.data.Pageable;
import org.navistack.framework.data.Sort;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MyBatisPlusUtils {

    @UtilityClass
    public class PageUtils {

        public <U> Page<U> toPage(IPage<U> source) {
            return new PageImpl<>(
                    source.getRecords(),
                    (int) source.getCurrent(),
                    (int) source.getSize(),
                    source.getTotal()
            );
        }

        public <U> IPage<U> fromPageable(Pageable source) {
            int current = source.getPageNumber();
            int size = source.getPageSize();
            List<OrderItem> orders = source.getSort().getOrders().stream()
                    .filter(order -> order.getProperty() != null && order.getDirection() != null)
                    .map(order -> new OrderItem(
                            order.getProperty(),
                            Sort.Direction.ASC.equals(order.getDirection()))
                    )
                    .collect(Collectors.toList());

            com.baomidou.mybatisplus.extension.plugins.pagination.Page<U> target = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
                    current,
                    size
            );
            target.addOrder(orders);

            return target;
        }
    }
}
