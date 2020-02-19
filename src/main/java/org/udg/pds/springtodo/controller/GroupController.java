package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.service.GroupService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path="/groups")
@RestController
public class GroupController extends BaseController {

  @Autowired
  GroupService groupService;

    @PostMapping(consumes = "application/json")
    public IdObject addGroup(HttpSession session, @Valid @RequestBody GroupController.R_Group group) {

        Long userId = getLoggedUser(session);

        if (group.name == null) {
            throw new ControllerException("No name supplied");
        }

        if (group.description == null) {
            throw new ControllerException("No description supplied");
        }

        return groupService.addGroup(group.name, group.description, userId);
    }

  @DeleteMapping(path="/{id}")
  public String deleteGroup(HttpSession session,
                             @PathVariable("id") Long groupId) {
    getLoggedUser(session);
    groupService.crud().deleteById(groupId);
    return BaseController.OK_MESSAGE;
  }

  static class R_Group {

    @NotNull
    public String name;

    @NotNull
    public String description;
  }

}
