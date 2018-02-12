using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class Home : TabbedPage
    {
        public Home()
        {
            InitializeComponent();
            //Children.Add(new NavigationPage(new BlogPosts()));
            //Children.Add(new NavigationPage(new FavouriteBlogPosts()));
        }
       
    }
}