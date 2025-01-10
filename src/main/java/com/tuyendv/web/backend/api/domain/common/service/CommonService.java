package com.tuyendv.web.backend.api.domain.common.service;

import org.springframework.data.domain.Pageable;

public interface CommonService {

    Pageable buildPageable(int page, int size, String sort);

}
