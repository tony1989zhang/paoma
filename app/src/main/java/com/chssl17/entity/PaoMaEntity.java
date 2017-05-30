package com.chssl17.entity;

import com.chssl17.http.JRParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by Administrator on 2017/5/29 0029.
 */
@HttpResponse(parser = JRParser.class)
public class PaoMaEntity {

    public int time;
    public CurrentBean current;
    public NextBean next;

    public static class CurrentBean {
        public long periodNumber;
        public long periodDate;
        public String awardTime;
        public String awardNumbers;
    }

    public static class NextBean {
        public String periodNumber;
        public String periodDate;
        public String awardTime;
        public int awardTimeInterval;
        public String delayTimeInterval;
    }
}
