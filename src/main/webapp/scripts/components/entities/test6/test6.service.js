'use strict';

angular.module('jhipsterApp')
    .factory('Test6', function ($resource, DateUtils) {
        return $resource('api/test6s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET', isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    for (var i = 0; i < data.length; i++) {
                        data[i].date = DateUtils.convertDateTimeFromServer(data[i].date);
                    };
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
