-- custom schema for tables, owner: user movie
create schema cinema authorization movie;
alter user movie set SEARCH_PATH=cinema,public;