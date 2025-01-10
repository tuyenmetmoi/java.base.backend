package com.tuyendv.web.backend.api.domain.common.impl;

import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.common.Constants;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.domain.common.service.CommonService;
import com.tuyendv.web.backend.api.util.Utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public Pageable buildPageable(int page, int size, String sort) {
        Pageable pageable;

        if (page <= 0 || size <= 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        int pageNumber = (page == 1) ? 0 : page - 1;

        if (sort != null && ! sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            String sortBy = sortParams[ 0 ].trim();
            String sortOrder = sortParams[ 1 ].trim();
            Sort.Direction direction;
            if (sortOrder.equalsIgnoreCase(Constants.SORT_ASC)) {
                direction = Sort.Direction.ASC;
            } else {
                direction = Sort.Direction.DESC;
            }

            String sortBySnakeCase = Utils.camelToSnake(sortBy);
            pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortBySnakeCase));
        } else {
            pageable = PageRequest.of(pageNumber, size);
        }
        return pageable;
    }

}
