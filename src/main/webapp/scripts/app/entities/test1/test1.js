'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test1', {
                parent: 'entity',
                url: '/test1s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test1.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test1/test1s.html',
                        controller: 'Test1Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test1');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test1.detail', {
                parent: 'entity',
                url: '/test1/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test1.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test1/test1-detail.html',
                        controller: 'Test1DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test1');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test1', function($stateParams, Test1) {
                        return Test1.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test1.new', {
                parent: 'test1',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test1/test1-dialog.html',
                        controller: 'Test1DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {firstname: null, age: null, date: null, time: null, flag: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test1', null, { reload: true });
                    }, function() {
                        $state.go('test1');
                    })
                }]
            })
            .state('test1.edit', {
                parent: 'test1',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test1/test1-dialog.html',
                        controller: 'Test1DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test1', function(Test1) {
                                return Test1.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test1', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
