// 认证工具类
const Auth = {
  // 检查是否已登录
  isLoggedIn() {
    const token = localStorage.getItem('token');
    const user = localStorage.getItem('user');
    return !!(token && user);
  },

  // 获取当前用户信息
  getCurrentUser() {
    try {
      const userStr = localStorage.getItem('user');
      return userStr ? JSON.parse(userStr) : null;
    } catch (e) {
      console.error('解析用户信息失败', e);
      return null;
    }
  },

  // 获取token
  getToken() {
    return localStorage.getItem('token');
  },

  // 保存登录信息
  saveAuth(token, user) {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
  },

  // 清除登录信息
  clearAuth() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  // 需要登录才能访问的页面保护
  requireAuth(redirectUrl = '/login.html') {
    if (!this.isLoggedIn()) {
      alert('请先登录');
      window.location.href = redirectUrl;
      return false;
    }
    return true;
  },

  // 显示用户信息（在页面头部）
  showUserInfo(elementId = 'userInfo') {
    const user = this.getCurrentUser();
    const element = document.getElementById(elementId);
    
    if (element && user) {
      element.innerHTML = `
        <div style="display: flex; align-items: center; justify-content: space-between; padding: 8px 16px; background: #f0f8ff; border-radius: 8px; margin-bottom: 16px;">
          <div>
            <div style="font-size: 14px; font-weight: 600; color: #1976d2;">${user.companyName || '用户'}</div>
            <div style="font-size: 12px; color: #888;">${user.email}</div>
          </div>
          <a href="/profile.html" style="color: #1976d2; text-decoration: none; font-size: 14px;">个人中心 →</a>
        </div>
      `;
    }
  },

  // 发起带token的API请求
  async fetchWithAuth(url, options = {}) {
    const token = this.getToken();
    
    if (!token) {
      throw new Error('未登录');
    }

    const headers = {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
      ...options.headers
    };

    try {
      const response = await fetch(url, {
        ...options,
        headers
      });

      // 如果返回401，说明token过期
      if (response.status === 401) {
        alert('登录已过期，请重新登录');
        this.clearAuth();
        window.location.href = '/login.html';
        return null;
      }

      return response;
    } catch (error) {
      console.error('API请求失败：', error);
      throw error;
    }
  }
};

// 导出给window，方便全局使用
window.Auth = Auth;
