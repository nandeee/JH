'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test7', {
                parent: 'entity',
                url: '/test7s',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test7.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test7/test7s.html',
                        controller: 'Test7Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test7');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test7.detail', {
                parent: 'entity',
                url: '/test7/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.test7.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test7/test7-detail.html',
                        controller: 'Test7DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test7');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Test7', function($stateParams, Test7) {
                        return Test7.get({id : $stateParams.id});
                    }]
                }
            })
            .state('test7.new', {
                parent: 'test7',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test7/test7-dialog.html',
                        controller: 'Test7DialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, age: null, date: null, etad: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('test7', null, { reload: true });
                    }, function() {
                        $state.go('test7');
                    })
                }]
            })
            .state('test7.edit', {
                parent: 'test7',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/test7/test7-dialog.html',
                        controller: 'Test7DialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Test7', function(Test7) {
                                return Test7.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('test7', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
