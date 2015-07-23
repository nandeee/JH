'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test8', {
                parent: 'entity',
                url: '/test8s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test8.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test8/test8s.html',
                        controller: 'Test8Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test8');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test8.detail', {
                parent: 'entity',
                url: '/test8/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test8.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test8/test8-detail.html',
                        controller: 'Test8DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test8');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test8', function($stateParams, Test8) {
                        return Test8.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test8.new', {
                parent: 'test8',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test8/test8-dialog.html',
                        controller: 'Test8DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, age: null, date: null, time: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test8', null, { reload: true });
                    }, function() {
                        $state.go('test8');
                    })
                }]
            })
            .state('test8.edit', {
                parent: 'test8',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test8/test8-dialog.html',
                        controller: 'Test8DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test8', function(Test8) {
                                return Test8.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test8', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
