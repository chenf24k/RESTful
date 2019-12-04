package com.chenf24k.restful;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
    private static final Log log = LogFactory.getLog(TvSeriesController.class);
    /*
    @GetMapping()
    public Map<String, Object> sayHello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "hello,world");
        return result;
    }
    */

    @GetMapping("/getAll")
    public List<TvSeriesDto> getAll() {
        if (log.isTraceEnabled()) {
            log.trace("getAll();被调用了");
        }
        List<TvSeriesDto> list = new ArrayList<>();
        list.add(createWestWorld());
        list.add(createPoi());
        return list;
    }


    @GetMapping("/{id}")
    public TvSeriesDto getOne(@PathVariable int id) {
        if (log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        switch (id) {
            case 101:
                return createWestWorld();
            case 102:
                return createPoi();
            default:
                throw new ResourceNotFoundException();
        }
    }

    @PostMapping("/add")
    public TvSeriesDto insertOne(@RequestBody TvSeriesDto tvSeriesDto) {
        if (log.isTraceEnabled()) {
            log.trace("这里应该些新增tvSeriesDto到数据库的代码，传递来的参数是：" + tvSeriesDto);
        }
        tvSeriesDto.setId(999);
        return tvSeriesDto;
    }

    @PutMapping("/{id}")
    public TvSeriesDto updateOne(@PathVariable int id, @RequestBody TvSeriesDto tvSeriesDto) {
        if (log.isTraceEnabled()) {
            log.trace("updateOne" + id);
        }
        switch (id) {
            case 101:
                return createWestWorld();
            case 102:
                return createPoi();
            default:
                throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request,
                                         @RequestParam(value = "delete_reason", required = false) String deleteReason) {
        if (log.isTraceEnabled()) {
            log.trace("deleteOne" + id);
        }
        Map<String, String> result = new HashMap<>();
        if (id == 101) {
            // TODO
            result.put("message", "#101被" + request.getRemoteAddr() + "删除(原因：" + deleteReason + ")");
        } else if (id == 102) {
            // 不能删除这个，RuntimeException不如org.springframework.security.access.AccessDeniedException更合适
            // 在后续章节中使用
            throw new RuntimeException("#102不能被删除");
        } else {
            throw new ResourceNotFoundException();
        }
        return result;
    }

    @PostMapping(value = "/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPhoto(@PathVariable int id, @RequestParam("photo") MultipartFile imgFile) throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("接收到文件" + id + "收到文件：" + imgFile.getOriginalFilename());
        }
        // 保存文件
        FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
        IOUtils.copy(imgFile.getInputStream(), fos);
        fos.close();
    }

    @GetMapping(value = "/{id}/icon", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getIcon(@PathVariable int id) throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("getIcon(" + id + ")");
        }
        String iconFile = "src/main/resources/郭贤081--12寸妮雅.jpg";
        InputStream is = new FileInputStream(iconFile);
        return IOUtils.toByteArray(is);
    }asdfasdf

    private TvSeriesDto createPoi() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.SEPTEMBER, 22, 0, 0);
        return new TvSeriesDto(102, "Person of Interest", 5, calendar.getTime());
    }

    private TvSeriesDto createWestWorld() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.OCTOBER, 2, 0, 0);
        return new TvSeriesDto(101, "West World", 1, calendar.getTime());
    }
}
