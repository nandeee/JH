'use strict';

angular.module('jhipsterApp')
    .factory('Test8', function ($resource, DateUtils) {
        return $resource('api/test8s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    for (var i = 0; i < data.length; i++) {
                        data[i].date = DateUtils.convertLocaleDateFromServer(data[i].date);
                        data[i].time = DateUtils.convertDateTimeFromServer(data[i].time);
                    };
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });
    });
