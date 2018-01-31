using CryptoNews.Models;
using CryptoNews.Services;
using CryptoNews.ViewModels;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class BlogPosts : ContentPage
    {
        

        public BlogPosts()
        {
          
            InitializeComponent();
            BindingContext = new BlogPostsViewModel(this.Navigation);
            
        }


        private async void OnSinglePostSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
            {
                return;
            }

            var SinglePost = e.SelectedItem as BlogPost;
            ((ListView)sender).SelectedItem = null;

            await Navigation.PushAsync(new SinglePost(SinglePost.Link));
           
        }

        async private void OnManageButtonClicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new BlogWebsites());
        }
    }
}