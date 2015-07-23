'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test6', {
                parent: 'entity',
                url: '/test6s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test6.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test6/test6s.html',
                        controller: 'Test6Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test6');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test6.detail', {
                parent: 'entity',
                url: '/test6/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test6.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test6/test6-detail.html',
                        controller: 'Test6DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test6');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test6', function($stateParams, Test6) {
                        return Test6.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test6.new', {
                parent: 'test6',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test6/test6-dialog.html',
                        controller: 'Test6DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, age: null, date: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test6', null, { reload: true });
                    }, function() {
                        $state.go('test6');
                    })
                }]
            })
            .state('test6.edit', {
                parent: 'test6',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test6/test6-dialog.html',
                        controller: 'Test6DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test6', function(Test6) {
                                return Test6.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test6', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
