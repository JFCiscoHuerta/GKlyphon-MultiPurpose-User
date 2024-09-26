
package com.gklyphon.User.controller;

import com.gklyphon.User.model.dto.UserDTO;
import com.gklyphon.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get a user by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Found the user",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "204", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access forbidden: insufficient permissions", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(
            @Parameter(description = "id of user to be searched")
            @PathVariable(name = "id") Long id) {
        UserDTO userDTO = userService.findById(id);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Gets all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all users",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "204", description = "No users Found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access forbidden: insufficient permissions", content = @Content)
    })
    @GetMapping("/all-users")
    public ResponseEntity<?> findAllUsers() {
        List<UserDTO> userDTOS = userService.findAll();
        if (!userDTOS.isEmpty()) {
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enable or disable a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully enabled/disabled",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Access forbidden: insufficient permissions", content = @Content)
    })
    @PostMapping("/toggle-user/{id}")
    public ResponseEntity<?> enableUser(
            @Parameter(description = "ID of the user to be enabled/disabled")
            @PathVariable(name = "id") Long id,
            @Parameter(description = "True to enable the user, false to disable")
            @RequestParam(name = "enabled") Boolean enabled
            ) {
        UserDTO userDTO = (enabled) ? userService.enableUser(id) : userService.disableUser(id);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
