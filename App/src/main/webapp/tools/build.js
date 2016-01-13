({
    appDir: '../www',
    mainConfigFile: '../www/js/config/requireConfig.js',
    waitSeconds: 0,
    dir: '../www-built',
    baseUrl: 'js',
    skipDirOptimize: false,
    generateSourceMaps: true,
    preserveLicenseComments: false,
    optimizeCss: 'standard',
    fileExclusionRegExp: /^test/,

    //uglify2 so that source maps can be generated
    optimize: 'uglify2',

    modules: [
		{
			name: 'app'
		}
    ]
})