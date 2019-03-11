'use strict'; 
 
angular.module('app')
.factory('myStorage', ['$http', function($http) {

        var myStorage ={

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

        return myStorage

    }])
