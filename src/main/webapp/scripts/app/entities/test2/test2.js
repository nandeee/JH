'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test2', {
                parent: 'entity',
                url: '/test2s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test2.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test2/test2s.html',
                        controller: 'Test2Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test2');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test2.detail', {
                parent: 'entity',
                url: '/test2/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test2.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test2/test2-detail.html',
                        controller: 'Test2DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test2');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test2', function($stateParams, Test2) {
                        return Test2.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test2.new', {
                parent: 'test2',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test2/test2-dialog.html',
                        controller: 'Test2DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {age: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test2', null, { reload: true });
                    }, function() {
                        $state.go('test2');
                    })
                }]
            })
            .state('test2.edit', {
                parent: 'test2',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test2/test2-dialog.html',
                        controller: 'Test2DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test2', function(Test2) {
                                return Test2.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test2', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
