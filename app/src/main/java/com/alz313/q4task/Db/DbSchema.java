package com.alz313.q4task.Db;

public class DbSchema {
    public static final class TaskTable{
        public static final String NAME = "task";

        public static final class Cols {
            public static final String ID = "_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String IMPORTANCE = "importance";
            public static final String URGENCY = "urgency";

            public static final String ESTIMATE = "estimate";
            public static final String IS_SOLVED = "is_solved";
            public static final String IS_FROG = "is_frog";

            public static final String DEADLINE = "deadline";
            public static final String RESOURCE = "resource";
            public static final String LOCATION = "location";

        }
    }
}
