using CryptoNews.Models;
using CryptoNews.ViewModels;
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
    public partial class FavouriteBlogPosts : ContentPage
    {
        public FavouriteBlogPosts()
        {
            InitializeComponent();
            BindingContext = new BlogPostsViewModel(true);
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

        private void DeleteClicked(object sender, EventArgs e)
        {
            
        }
    }
}