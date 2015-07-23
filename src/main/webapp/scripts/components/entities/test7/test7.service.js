'use strict';

angular.module('jhipsterApp')
    .factory('Test7', function ($resource, DateUtils) {
        return $resource('api/test7s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    for (var i = 0; i < data.length; i++) {
                    data[i].date = DateUtils.convertDateTimeFromServer(data[i].date);
                    data[i].etad = DateUtils.convertLocaleDateFromServer(data[i].etad);
                    };
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.etad = DateUtils.convertLocaleDateToServer(data.etad);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.etad = DateUtils.convertLocaleDateToServer(data.etad);
                    return angular.toJson(data);
                }
            }
        });
    });
