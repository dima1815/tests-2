package com.mycomp.execspec.jiraplugin.service;

import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompleteDTO;

import java.util.List;

/**
 * Created by Dmytro on 5/15/2014.
 */
public interface AutoCompleteService {

    List<AutoCompleteDTO> autoComplete(String projectKey, String input);

}
