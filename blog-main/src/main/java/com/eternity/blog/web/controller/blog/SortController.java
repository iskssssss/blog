package com.eternity.blog.web.controller.blog;

import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import com.eternity.blog.common.random.RandomDevice;
import com.eternity.blog.common.utils.BooleanUtils;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.JsonUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.web.dto.TableDTO;
import com.eternity.blog.system.domain.blog.SortModel;
import com.eternity.blog.system.service.ISortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 分类请求控制器
 * @Author eternity
 * @Date 2020/4/21 20:33
 */
@Controller
public class SortController extends BaseController {


    @Autowired
    ISortService sortService;


    @ResponseBody
    @GetMapping("/sort/getSortNameList")
    public BaseAjaxResult getLabelNameList() {
        return success(sortService.selectAll());
    }

    @ResponseBody
    @GetMapping("/sort/getSortList")
    public String getSortList(@RequestParam int limit, @RequestParam int page) {
        List<SortModel> sortModels = sortService.selectLimit((page - 1) * limit, limit);
        Long size = sortService.selectAllCount();
        TableDTO tableDTO = new TableDTO();
        tableDTO.setCode(0);
        tableDTO.setMsg("");
        tableDTO.setCount(size);
        tableDTO.setData(sortModels);
        return JsonUtils.toJsonString(tableDTO);
    }

    @ResponseBody
    @GetMapping("/sort/addSort")
    public BaseAjaxResult addSort(SortModel sortModel) {
        if (BooleanUtils.toBoolean(sortService.checkSortByName(sortModel.getsName()))) {
            return error("添加失败,该分类已存在.");
        }
        sortModel.setsSid(getSid());
        sortModel.setsAddDate(DateUtils.getDateTime());
        if (!BooleanUtils.toBoolean(sortService.insertSort(sortModel))) {
            return error("添加失败.");
        }
        return success("添加成功.");
    }

    @ResponseBody
    @GetMapping("/sort/updateSort")
    public BaseAjaxResult updateSort(SortModel sortModel) {
        if (StringUtils.isEmpty(sortModel.getsName())) {
            return error("修改失败,标签名称不可为空.");
        }
        if (!BooleanUtils.toBoolean(sortService.checkSortById(sortModel.getsSid()))) {
            return error("修改失败,该标签不存在.");
        }
        if (StringUtils.isNotNull(sortModel.getsName()) &&
                BooleanUtils.toBoolean(sortService.checkSortByName(sortModel.getsName()))) {
            return error("修改失败,该标签已存在.");
        }
        if (!BooleanUtils.toBoolean(sortService.updateSort(sortModel))) {
            return error("修改失败,请刷新后重试.");
        }
        return success("修改成功.");
    }

    @ResponseBody
    @GetMapping("/sort/deleteSort")
    public BaseAjaxResult updateSort(@RequestParam("sSid") Long sSid) {
        if (!BooleanUtils.toBoolean(sortService.checkSortById(sSid))) {
            return error("删除失败,该分类不存在.");
        }
        if (BooleanUtils.toBoolean(sortService.checkSortUse(sSid))){
            return error("删除失败,该分类使用中.");
        }
        if (!BooleanUtils.toBoolean(sortService.deleteSortByLid(sSid))) {
            return error("删除失败.");
        }
        return success("删除成功.");
    }

    /**
     * 获取SId
     *
     * @return lId
     */
    private Long getSid() {
        long sId = RandomDevice.randomId();

        if (BooleanUtils.toBoolean(sortService.checkSortById(sId))) {
            getSid();
        }
        return sId;
    }
}