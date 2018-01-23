using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SinglePost : ContentPage
    {
        public SinglePost(string blogPostUrl)
        {
            InitializeComponent();
            
            webView.Source = blogPostUrl;
        }

        protected async override void OnAppearing()
        {
            base.OnAppearing();
            await progressBar.ProgressTo(0.9, 900, Easing.SpringIn);

        }

        private void webView_Navigating(object sender, WebNavigatingEventArgs e)
        {
            progressBar.IsVisible = true;
        }

        private void webView_Navigated(object sender, WebNavigatedEventArgs e)
        {
            progressBar.IsVisible = false;

        }

    }

}