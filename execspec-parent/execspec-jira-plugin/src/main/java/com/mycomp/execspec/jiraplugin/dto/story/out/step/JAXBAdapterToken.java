package com.mycomp.execspec.jiraplugin.dto.story.out.step;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Dmytro on 4/11/2014.
 */
public class JAXBAdapterToken extends XmlAdapter<BaseToken, IToken> {

    @Override
    public IToken unmarshal(BaseToken v) throws Exception {
        return v;
    }

    @Override
    public BaseToken marshal(IToken v) throws Exception {
        return (BaseToken) v;
    }
}
