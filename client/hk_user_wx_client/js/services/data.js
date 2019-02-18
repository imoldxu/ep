/**
 * Created by chen on 2016/3/18.
 */
/**
 */
define(['jquery','app'], function ($,app) {

    app.factory('dataVer', ['$http', function($http) {

        var dataVer ={

            get : function(i){

                if (localStorage[i] == undefined || localStorage[i] ==  null){

                    return null

                }

                return JSON.parse(localStorage[i]);
            },

            put : function(i,data){

                localStorage[i] = JSON.stringify(data);

            }

        };

        return dataVer

    }])

})
