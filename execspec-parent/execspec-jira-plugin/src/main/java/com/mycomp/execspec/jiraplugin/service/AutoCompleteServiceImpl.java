package com.mycomp.execspec.jiraplugin.service;

import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompleteDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmytro on 5/15/2014.
 */
public class AutoCompleteServiceImpl implements AutoCompleteService {

    @Override
    public List<AutoCompleteDTO> autoComplete(String projectKey, String input) {

        List<AutoCompleteDTO> results = new ArrayList<AutoCompleteDTO>();

        AutoCompleteDTO entry = new AutoCompleteDTO("stubbed server response", 0);
        results.add(entry);

        return results;
    }
}
