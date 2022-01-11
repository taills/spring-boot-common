;;;
/**
 * 外挂注入 Swagger，让设置的 token 保存在浏览器存储里
 */
(function() {
    const TOKEN_NAME = 'JwtToken';
    setTimeout(function() {
        const JWTToken = localStorage.getItem(TOKEN_NAME);
        if (JWTToken) {
            console.log('[Swagger] Read token from localStorage');
            ui.authActions.preAuthorizeImplicit({
                auth: {
                    schema: {
                        type: 'apiKey',
                        in: 'header',
                        name: 'Authorization',
                        get: function(key) {
                            return this[key];
                        },
                    },
                    name: 'Authorization',
                    value: JWTToken,
                },
                token: {},
                isValid: true,
            });
        }
        const openBtn = document.querySelector('#swagger-ui > section > div.swagger-ui > div:nth-child(2) > div.scheme-container > section > div.auth-wrapper > button');
        if (openBtn) {
            openBtn.addEventListener('click', function() {
                setTimeout(function() {
                    const authBtn = document.querySelector('#swagger-ui > section > div.swagger-ui > div:nth-child(2) > div.scheme-container > section > div.auth-wrapper > div > div.modal-ux > div > div > div.modal-ux-content > div > form > div.auth-btn-wrapper > button.btn.modal-btn.auth.authorize.button');
                    authBtn.addEventListener('click', function(e) {
                        const tokenInput = document.querySelector('#swagger-ui > section > div.swagger-ui > div:nth-child(2) > div.scheme-container > section > div.auth-wrapper > div > div.modal-ux > div > div > div.modal-ux-content > div > form > div:nth-child(1) > div > div:nth-child(5) > section > input[type=text]');
                        if (tokenInput && tokenInput.value) {
                            localStorage.setItem(TOKEN_NAME, tokenInput.value);
                            console.log('[Swagger] Token was successfully saved!!!');
                        }
                    });
                }, 1000);
            });
        }
    }, 1000);
})();