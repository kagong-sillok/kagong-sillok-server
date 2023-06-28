package org.prography.kagongsillok.tag.ui;

import lombok.RequiredArgsConstructor;
import org.prography.kagongsillok.tag.application.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagV1Controller {

    private final TagService tagService;


}
