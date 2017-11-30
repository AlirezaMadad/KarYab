using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(KarYab.Startup))]
namespace KarYab
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
