package web.filter;

import util.HibernateUtil;
import javax.servlet.*;
import org.hibernate.SessionFactory;
import javax.servlet.annotation.WebFilter;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author root
 */
@WebFilter(urlPatterns = {"*.jsf"})
public class ConexaoHibernateFilter implements Filter{
    private SessionFactory sf;
    public void init(FilterConfig config) throws ServletException {
        this.sf = HibernateUtil.getSessionFactory();
    }
    
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, 
            FilterChain chain) throws ServletException{
            Session currentSession = this.sf.getCurrentSession();
            
            Transaction transaction = null;
            
            try {
                transaction = currentSession.beginTransaction();
                chain.doFilter(servletRequest, servletResponse);
                transaction.commit();
                if (currentSession.isOpen()) {
                    currentSession.close();
                }
            } catch (Throwable ex) {
                try {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                throw new ServletException(ex);
                    }
                }

    @Override
    public void destroy() {}   
}
                