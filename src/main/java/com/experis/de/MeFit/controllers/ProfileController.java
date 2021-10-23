package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Profile;
import com.experis.de.MeFit.repositories.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    //get all profiles
    @Operation(summary = "Get all profiles without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of profiles",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "No profiles found",
                    content = @Content) })
    @GetMapping()
    public List<Profile> getAllProfiles(){
        return this.profileRepository.findAll();
    }

    //get profile by id
    @Operation(summary = "Get a specific profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "No profile found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable(value="id") long profileId) {

        HttpStatus status;
        Profile profile = profileRepository.getById(profileId);

        status = (profile != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(profile, status);
    }

    //save profile
    @Operation(summary = "Save profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        return new ResponseEntity<Profile>(profileRepository.save(profile), HttpStatus.CREATED);
    }

    //update profile
    @Operation(summary = "Update profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable(value="id") long profileId,
                                                   @RequestBody Profile profileDetails) {

        Profile profile = profileRepository.getById(profileId);

        profile.setPerson(profileDetails.getPerson());
        profile.setFitnessLevel(profileDetails.getFitnessLevel());
        profile.setHeight(profileDetails.getHeight());
        profile.setWeight(profileDetails.getWeight());
        profile.setGoal(profileDetails.getGoal());


        return ResponseEntity.ok(this.profileRepository.save(profile));
    }

    //delete profile
    @Operation(summary = "Delete profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable(value = "id") long profileId){

        Profile profile = profileRepository.getById(profileId);

        this.profileRepository.delete(profile);
    }

    //Delete all profiles
    @Operation(summary = "Delete all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All profiles deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllProfiles(){
        this.profileRepository.deleteAll();
    }
}
