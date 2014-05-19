package com.mycomp.execspec.jiraplugin.service;

import com.mycomp.execspec.jiraplugin.dto.autocomplete.AutoCompleteDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dmytro on 5/15/2014.
 */
public class AutoCompleteServiceImpl implements AutoCompleteService {

    @Override
    public List<AutoCompleteDTO> autoComplete(String projectKey, String input) {

        List<AutoCompleteDTO> results = new ArrayList<AutoCompleteDTO>();

        AutoCompleteDTO entry = new AutoCompleteDTO("WWWW W WWWW WWWW WWWW WWWW WWWW WWWW WWWW WWWW WWWW WWWW WWWW WWWW \nstubbed server response - "
                + new SimpleDateFormat().format(new Date()), 0);
        results.add(entry);

        return results;
    }
}
