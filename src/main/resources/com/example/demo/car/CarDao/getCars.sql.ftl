<#include "com/example/demo/Util.sql.ftl" />
<@getCar /> where carid in (<@getCarId />) and carid = :test