'use strict';

angular.module('jhipsterApp')
    .factory('Test3', function ($resource, DateUtils) {
        return $resource('api/test3s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.five = DateUtils.convertLocaleDateFromServer(data.five);
                    data.six = DateUtils.convertDateTimeFromServer(data.six);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.five = DateUtils.convertLocaleDateToServer(data.five);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.five = DateUtils.convertLocaleDateToServer(data.five);
                    return angular.toJson(data);
                }
            }
        });
    });
