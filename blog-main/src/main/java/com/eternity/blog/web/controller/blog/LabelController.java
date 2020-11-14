package com.eternity.blog.web.controller.blog;

import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import com.eternity.blog.common.random.RandomDevice;
import com.eternity.blog.common.utils.BooleanUtils;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.JsonUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.web.dto.TableDTO;
import com.eternity.blog.system.service.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 标签请求控制器
 * @Author eternity
 * @Date 2020/4/21 20:32
 */
@Controller
public class LabelController extends BaseController {

    @Autowired
    ILabelService labelService;

    @ResponseBody
    @GetMapping("/label/getLabelNameList")
    public BaseAjaxResult getLabelNameList() {
        return success(labelService.selectAll());
    }

    @ResponseBody
    @GetMapping("/label/getLabelList")
    public String getLabelList(@RequestParam int limit, @RequestParam int page) {
        List<LabelModel> label = labelService.selectLimit((page - 1) * limit, limit);
        Long size = labelService.selectAllCount();
        TableDTO tableDTO = new TableDTO();
        tableDTO.setCode(0);
        tableDTO.setMsg("");
        tableDTO.setCount(size);
        tableDTO.setData(label);
        return JsonUtils.toJsonString(tableDTO);
    }

    @ResponseBody
    @GetMapping("/label/addLabel")
    public BaseAjaxResult addLabel(LabelModel labelModel) {
        if (BooleanUtils.toBoolean(labelService.checkLabelByName(labelModel.getlName()))) {
            return error("blog.label.add.not.exists");
        }
        labelModel.setlLid(getLid());
        labelModel.setlAddDate(DateUtils.getDateTime());
        if (!BooleanUtils.toBoolean(labelService.insertLabel(labelModel))) {
            return error("blog.label.add.error");
        }
        return success("blog.label.add.success");
    }

    @ResponseBody
    @GetMapping("/label/updateLabel")
    public BaseAjaxResult updateLabel(LabelModel labelModel) {
        if (StringUtils.isEmpty(labelModel.getlName())) {
            return error("blog.label.modify.name.not.empty");
        }
        if (!BooleanUtils.toBoolean(labelService.checkLabelById(labelModel.getlLid()))) {
            return error("blog.label.modify.not.exists");
        }
        if (StringUtils.isNotNull(labelModel.getlName()) &&
                BooleanUtils.toBoolean(labelService.checkLabelByName(labelModel.getlName()))) {
            return error("blog.label.modify.exists");
        }
        if (!BooleanUtils.toBoolean(labelService.updateLabel(labelModel))) {
            return error("blog.label.modify.error");
        }
        return success("blog.label.modify.success");
    }

    @ResponseBody
    @GetMapping("/label/deleteLabel")
    public BaseAjaxResult deleteLabel(@RequestParam("lId") Long lId) {
        if (!BooleanUtils.toBoolean(labelService.checkLabelById(lId))) {
            return error("blog.label.delete.not.exists");
        }
        if (BooleanUtils.toBoolean(labelService.checkLabelUse(lId))){
            return error("删除失败,该标签使用中.");
        }
        if (!BooleanUtils.toBoolean(labelService.deleteLabelByLid(lId))) {
            return error("blog.label.delete.error");
        }
        return success("blog.label.delete.success");
    }

    /**
     * 获取lId
     *
     * @return lId
     */
    private Long getLid() {
        long lId = RandomDevice.randomId();
        if (BooleanUtils.toBoolean(labelService.checkLabelById(lId))) {
            getLid();
        }
        return lId;
    }
}
