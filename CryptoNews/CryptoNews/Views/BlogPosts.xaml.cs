using CryptoNews.Models;
using CryptoNews.Services;

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
        private bool _isBusy;
        public bool IsBusy
        {
            get { return _isBusy; }
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }

        public List<BlogPost> FinalBlogPosts { get; set; }

        public BlogPosts()
        {
          
            InitializeComponent();
            IsBusy = true;
            FinalBlogPosts = App.database.GetAllBlogPosts();
            BlogPostsListView.ItemsSource = FinalBlogPosts;
            InitAsync();
            
        }

        private async Task InitAsync()
        {
            var service = new PostsRepository();
            FinalBlogPosts = await service.GetAllPostsAsync();
            BlogPostsListView.ItemsSource = FinalBlogPosts;
            IsBusy = false;

            //await App.database.AddUpdatedBlogPosts(UpdatedPosts);
            //    BlogPosts = new ObservableCollection<BlogPost>(App.database.GetAllBlogPosts());
        }

        private async void OnSinglePostSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
            {
                return;
            }

            var SinglePost = e.SelectedItem as BlogPost;


            await Navigation.PushAsync(new SinglePost(SinglePost.Link));
           
        }
    }
}